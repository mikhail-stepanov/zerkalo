package zerkalo.msclient.user.interfaces;

import zerkalo.msclient.user.models.UserCertificateAddRequest;
import zerkalo.msclient.user.models.UserCertificateRequest;
import zerkalo.msclient.user.models.UserCertificateResponse;

public interface IUserCertificates {

    String USER_CERTIFICATES_LIST = "/v1/api/user/certificates/list";
    String USER_CERTIFICATES_INFO = "/v1/api/user/certificates/info";
    String USER_CERTIFICATES_ADD = "/v1/api/user/certificates/add";
    String USER_CERTIFICATES_REMOVE = "/v1/api/user/certificates/remove";



    UserCertificateResponse[] list();

    UserCertificateResponse add(UserCertificateAddRequest request);

    UserCertificateResponse info(UserCertificateRequest request);

    UserCertificateResponse remove(UserCertificateRequest request);
}
