package com.sigma.towerdepoyms.util.git;

import com.sigma.towerdepoyms.config.DeployConfig;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author zhenpeng
 */
@Component
public class CommitFile {

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
}
