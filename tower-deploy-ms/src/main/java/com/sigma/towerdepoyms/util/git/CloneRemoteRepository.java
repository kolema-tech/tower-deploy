package com.sigma.towerdepoyms.util.git;

import com.sigma.towerdepoyms.config.DeployConfig;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author zhenpeng
 */
@Component
public class CloneRemoteRepository {

    @Autowired
    private DeployConfig deployConfig;

    public void cloneRemote(String ms, String gitUrl) throws GitAPIException {

        File gitDir = new File(deployConfig.getGitPath(), ms);
        if (!gitDir.getParentFile().exists()) {
            gitDir.getParentFile().mkdirs();
        }

        // then clone
        System.out.println("Cloning from " + gitUrl + " to " + gitDir);
        try (Git result = Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(gitDir)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        }
    }
}
