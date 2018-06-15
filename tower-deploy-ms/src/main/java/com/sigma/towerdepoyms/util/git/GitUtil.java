package com.sigma.towerdepoyms.util.git;

import com.google.common.collect.Lists;
import com.sigma.towerdepoyms.config.DeployConfig;
import com.sigma.towerdepoyms.entity.MsVersion;
import lombok.experimental.var;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/15-11:28
 * desc: git 工具
 **/
@Component
public class GitUtil {

    @Autowired
    private DeployConfig deployConfig;

    public void commitAndPush(File repoDir, String commitFileName, String commitMessage) throws IOException, GitAPIException {

        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repository = builder
                .readEnvironment()
                .findGitDir(repoDir)
                .build()) {

            System.out.println("Having repository: " + repository.getDirectory());

            Git git = new Git(repository);

            git.checkout()
                    .setName("master")
                    .call();

            Status status = git.status().call();
            System.out.println("Added: " + status.getAdded());
            System.out.println("Changed: " + status.getChanged());
            System.out.println("Conflicting: " + status.getConflicting());
            System.out.println("ConflictingStageState: " + status.getConflictingStageState());
            System.out.println("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
            System.out.println("Missing: " + status.getMissing());
            System.out.println("Modified: " + status.getModified());
            System.out.println("Removed: " + status.getRemoved());
            System.out.println("Untracked: " + status.getUntracked());
            System.out.println("UntrackedFolders: " + status.getUntrackedFolders());

            if (status.isClean()) {
                return;
            }

            // run the add
            git.add()
                    .addFilepattern(commitFileName)
                    .call();

            // and then commitAndPush the changes
            git.commit()
                    .setMessage(commitMessage)
                    .call();

            //push to the remote
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider(deployConfig.getUsername(), deployConfig.getPassword());

            git.push()
                    .setCredentialsProvider(cp)
                    .call();

            System.out.println("Committed file " + commitFileName + " to repository at " + repository.getDirectory());
        }
    }

    public void gitCheckout(File repoDir, String version) throws IOException, GitAPIException {
        File repoGitDir = new File(repoDir.getAbsolutePath() + "/.git");
        if (repoGitDir.exists()) {
            var repo = new FileRepository(repoGitDir.getAbsolutePath());
            Git git = new Git(repo);
            CheckoutCommand checkout = git.checkout();
            checkout.setName(version);
            checkout.call();
        }
    }

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

    public List<MsVersion> getVersions(String name) throws IOException, GitAPIException {

        var repoDir = new File(deployConfig.getGitPath(), name);

        List<MsVersion> list = Lists.newArrayList();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repository = builder
                .readEnvironment()
                .findGitDir(repoDir)
                .build()) {

            System.out.println("Having repository: " + repository.getDirectory());

            Git git = new Git(repository);

            git.checkout()
                    .setName("master")
                    .call();

            var logs = git.log()
                    .all()
                    .call();

            logs.forEach(log -> {
                Instant instant = Instant.ofEpochMilli(log.getCommitTime());
                ZoneId zone = ZoneId.systemDefault();
                list.add(new MsVersion(log.getName(), LocalDateTime.ofInstant(instant, zone), log.getFullMessage()));
            });

        }

        return list;
    }
}
