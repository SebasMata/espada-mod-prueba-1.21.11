package com.mataflex;

import com.mataflex.entity.MysticalVikingRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ValhallaEchoesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(
				ValhallaEchoes.MYSTICAL_VIKING,
				MysticalVikingRenderer::new
		);
	}
}