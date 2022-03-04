package com.udemy.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {

	private int movimentoX = 0;
	private int movimentoY = 0;
	private SpriteBatch batch;
	private Texture passaro;
	private Texture fundo;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;

	@Override
	public void create () {

		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		larguraDispositivo = Gdx.graphics.getHeight();

	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(fundo, 0,0, alturaDispositivo, larguraDispositivo);
		batch.draw(passaro, movimentoX, movimentoY);

		movimentoX++;
		movimentoY++;
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
