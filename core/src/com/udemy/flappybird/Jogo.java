package com.udemy.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class Jogo extends ApplicationAdapter {

	//Texturas
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoTopo;

	//Formas para colisão
	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float gravidade = 2;
	private float posicaoInicialVerticalPassaro = 0;
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private float espacoEntreCanos;
	private Random random;
	private int pontos = 0;
	private boolean passouCano;

	//Exibição de textos
	BitmapFont textoPontuacao;

	@Override
	public void create () {

		inicializarTexturas();
		inicializarObjetos();

	}

	@Override
	public void render () {

		verificarEstadoJogo();
		validarPontos();
		desenharTexturas();
		detectarColisoes();

	}

	private void verificarEstadoJogo(){

		//Movimentar cano
		posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if (posicaoCanoHorizontal < -canoTopo.getWidth()){
			posicaoCanoHorizontal = larguraDispositivo;
			posicaoCanoVertical = random.nextInt(400) - 200;
			passouCano = false;
		}

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

	private void detectarColisoes(){

		circuloPassaro.set(
				50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth()/2
		);

		retanguloCanoBaixo.set(
				posicaoCanoHorizontal , alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical,
				canoBaixo.getWidth(), canoBaixo.getHeight()
		);

		retanguloCanoCima.set(
				posicaoCanoHorizontal , alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical,
				canoTopo.getWidth(), canoTopo.getHeight()
		);

		boolean colidiuCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
		boolean colidiuCanoBaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

		if (colidiuCanoCima || colidiuCanoBaixo){
			Gdx.app.log("log", "colidiu");
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.circle(50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth()/2);

		//topo
		shapeRenderer.rect(
				posicaoCanoHorizontal , alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical,
				canoTopo.getWidth(), canoTopo.getHeight()
		);

		//baixo
		shapeRenderer.rect(
				posicaoCanoHorizontal , alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical,
				canoBaixo.getWidth(), canoBaixo.getHeight()
		);

		shapeRenderer.end();

	}

	private void desenharTexturas(){

		batch.begin();

		batch.draw(fundo, 0,0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoHorizontal , alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal , alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo -110);
		batch.end();

	}

	public void validarPontos(){


		if (posicaoCanoHorizontal < 50 - passaros[0].getWidth()){ //Passou da posição do passaro
			if (!passouCano){
				pontos++;
				passouCano = true;
			}
		}

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
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 150;

		//Configurações dos textos
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

		//Formas geometricas para colisoes
		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoBaixo = new Rectangle();
		retanguloCanoCima = new Rectangle();

	}
	
	@Override
	public void dispose () {

	}
}
