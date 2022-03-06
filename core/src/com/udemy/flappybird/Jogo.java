package com.udemy.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {

	//Texturas
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoTopo;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float gravidade = 2;
	private float posicaoInicialVerticalPassaro = 0;
	private float posicaoCanoHorizontal = 0;
	private float espacoEntreCanos;

	@Override
	public void create () {

		inicializarTexturas();
		inicializarObjetos();

	}

	@Override
	public void render () {

		verificarEstadoJogo();
		desenharTexturas();

	}

	private void verificarEstadoJogo(){

		//Aplica evento de toque na tela
		boolean toqueTela = Gdx.input.justTouched();
		if (toqueTela){
			gravidade = -25;
		}

		//Aplica gravidade no passaro
		if (posicaoInicialVerticalPassaro > 0 || toqueTela )
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		variacao += Gdx.graphics.getDeltaTime() * 10;
		//Verifica variação para bater asas do passaro
		if (variacao > 3)
			variacao = 0;
		gravidade++;

	}

	private void desenharTexturas(){

		batch.begin();

		batch.draw(fundo, 0,0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], 0, posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoHorizontal -100, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2);
		batch.draw(canoTopo, posicaoCanoHorizontal -100, alturaDispositivo / 2 + espacoEntreCanos / 2);
		batch.end();

	}

	private void inicializarTexturas(){

		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");

	}

	private void inicializarObjetos(){

		batch = new SpriteBatch();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 150;

	}
	
	@Override
	public void dispose () {

	}
}
