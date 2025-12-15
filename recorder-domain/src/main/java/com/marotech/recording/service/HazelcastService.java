package com.marotech.recording.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.marotech.recording.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public User getCurrentUser(String sessionId){
        IMap map = hazelcastInstance.getMap(SESSION_MAP);
        String userId = (String) map.get(sessionId);
        if(userId == null){
            return  null;
        }
        return repositoryService.fetchUserById(userId);
    }
    public void setCurrentUser(User user, String sessionId){
        IMap map = hazelcastInstance.getMap(SESSION_MAP);
        map.put(sessionId, user.getId());
    }
    public static final String SESSION_MAP = "paystream_session_map";
}
