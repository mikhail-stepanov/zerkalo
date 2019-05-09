package com.tochka.common.endpoints;

import com.tochka.common.interfaces.IClearService;
import com.tochka.common.models.SettingsInformation;
import com.tochka.common.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MicroserviceInformationEndpoint {

    @Autowired(required = false)
    IClearService clearService;

    @Autowired(required = false)
    SettingsService settingsServices;

    @RequestMapping("common/settings")
    public SettingsInformation[] settings(){
        return settingsServices.settingsInformations();
    }

    @RequestMapping("common/dictionaries")
    public SettingsInformation[] dictionaries(){
        return settingsServices.settingsInformations();
    }

    @RequestMapping(value = "common/clear", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public void clear(){
        if(clearService != null)
            clearService.clear();
    }
}
