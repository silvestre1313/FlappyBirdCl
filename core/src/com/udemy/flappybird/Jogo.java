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
	private Texture[] passaros;
	private Texture fundo;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;

	@Override
	public void create () {

		batch = new SpriteBatch();
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

	}

	@Override
	public void render () {

		batch.begin();

		if (variacao > 3)
			variacao = 0;

		batch.draw(fundo, 0,0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], movimentoX, 500);

		variacao += Gdx.graphics.getDeltaTime() * 10;

		movimentoX++;
		movimentoY++;
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
