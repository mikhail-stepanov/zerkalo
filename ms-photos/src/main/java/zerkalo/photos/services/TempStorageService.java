package zerkalo.photos.services;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zerkalo.msclient.exceptions.MicroServiceException;
import zerkalo.msclient.exceptions.MsInternalErrorException;
import zerkalo.msclient.photos.interfaces.IPhotosService;
import zerkalo.msclient.photos.models.PhotosRequest;

import java.io.IOException;

@Service
public class TempStorageService {

    @Value("${profiles.saver.elastic.index.profiles}")
    private String elasticIndex;

    @Autowired
    private IPhotosService photosService;

    @Autowired
    private RestHighLevelClient elasticClient;

    public void savePhotosForAll() throws MicroServiceException {
        try {
            SearchRequest searchRequest = new SearchRequest(elasticIndex);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1400);
            searchRequest.source(searchSourceBuilder);
            searchRequest.scroll(TimeValue.timeValueMinutes(1L));
            SearchResponse searchResponse = elasticClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();

            hits.forEach(hit -> {
                try {
                    photosService.loadById(PhotosRequest.builder()
                            .id(hit.getId())
                            .build());
                } catch (MicroServiceException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            throw new MsInternalErrorException("Exception while getting profiles from ElasticSearch");
        }
    }


}
