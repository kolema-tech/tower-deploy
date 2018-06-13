package com.sigma.towerdepoyms.util.git;

import lombok.experimental.var;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author zhenpeng
 */

@Component
public class CheckoutFile {

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
}
