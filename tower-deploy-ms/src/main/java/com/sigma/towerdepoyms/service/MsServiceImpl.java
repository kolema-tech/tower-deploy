package com.sigma.towerdepoyms.service;

import com.sigma.towerdepoyms.entity.Ms;
import com.sigma.towerdepoyms.mapper.MsMapper;
import com.sigma.towerdepoyms.request.MsRequest;
import com.sigma.towerdepoyms.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhenpeng
 */
@Service
public class MsServiceImpl implements MsService {

    @Autowired
    MsMapper msMapper;

    @Override
    public int insertMs(MsRequest msRequest) {

        Ms ms = new Ms();
        ms.setName(msRequest.getName());
        ms.setGitUrl(msRequest.getGitUrl());

        if (msRequest.getConfigs() != null) {
            ms.setConfigs(JsonUtils.serialize(msRequest.getConfigs()));
        }

        return msMapper.insert(ms);
    }

    @Override
    public int updateMs(MsRequest msRequest) {
        Ms ms = msMapper.selectByName(msRequest.getName());
        if (ms == null) {
            return 0;
        }

        ms.setGitUrl(msRequest.getGitUrl());
        if (msRequest.getConfigs() != null) {
            ms.setConfigs(JsonUtils.serialize(msRequest.getConfigs()));
        }

        return msMapper.updateByPrimaryKeySelective(ms);
    }

    @Override
    public List<Ms> getAll() {
        return msMapper.selectAll();
    }

    @Override
    public Ms find(String msName) {
        return msMapper.selectByName(msName);
    }
}
