package org.ditto.keyboard.usecase;

import org.ditto.keyboard.repository.RepositoryFascade;

import javax.inject.Inject;

/**
 * Created by admin on 2017/6/25.
 */

public class EmojiUsecase {
    private RepositoryFascade repositoryFascade;

    @Inject
    public EmojiUsecase(RepositoryFascade repositoryFascade) {
        this.repositoryFascade = repositoryFascade;
    }

}
