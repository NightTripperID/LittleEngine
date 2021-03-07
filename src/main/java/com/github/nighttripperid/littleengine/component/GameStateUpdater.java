/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class GameStateUpdater {

    @Getter
    private final GameStateStackController gameStateStackController;

    public GameStateUpdater(GameStateStackController gameStateStackController) {
        this.gameStateStackController = gameStateStackController;
    }

    public void update() {
        addPendingEntities();
        List<Entity> entities = gameStateStackController.getActiveGameState().getEntityData().getEntities();
        entities.forEach(entity -> {
            entity.getRenderRequests().clear();
            runBehaviorScript(entity);
            runAnimationScript(entity,
                gameStateStackController.getActiveGameState().getEntityData().getEntityGFX().getSpriteMaps().get(entity.getFilename()));
            gameStateStackController.performGameStateTransition(entity.getGameStateTransition());
        });
        removeMarkedEntities();
    }

    // TODO: delegate entity methods to EntityProcessor (maybe)
    public void addEntity(Entity entity) {
        entity.onCreate();
        gameStateStackController.getActiveGameState().getEntityData().getPendingEntities().add(entity);
    }

    private void addPendingEntities() {
        gameStateStackController.getActiveGameState().getEntityData().getEntities()
                .addAll(gameStateStackController.getActiveGameState().getEntityData().getPendingEntities());
        gameStateStackController.getActiveGameState().getEntityData().getPendingEntities().clear();
    }

    private void removeMarkedEntities() {
        for(int i = 0; i < gameStateStackController.getActiveGameState().getEntityData().getEntities().size(); i++) {
            if(gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i).isRemoved()) {
                gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i).onDestroy();
                gameStateStackController.getActiveGameState().getEntityData().getEntities()
                        .remove(gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i--));
            }
        }
    }

    private void runAnimationScript(Entity entity, Map<Integer, Sprite> spriteMap) {
        if (entity.getAnimationScript() != null)
            entity.getAnimationScript().run(spriteMap);
    }

    private void runBehaviorScript(Entity entity) {
        // TODO: implement groovy integration for entity updates (maybe)
        if (entity.getBehaviorScript() != null)
            entity.getBehaviorScript().run(gameStateStackController.getActiveGameState().getGameMap());
    }
}
