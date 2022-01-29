package devinmoney.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {

	private Conta contaOrigem;
	private Conta contaDestino;
	private Double valor;
	private String data;

	public Transacao(Conta contaOrigem, Conta contaDestino, Double valor) {
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
		this.valor = valor;
		this.data = getDataFormatada();
	}

	private String getDataFormatada() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return agora.format(formatador);
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Transação: Conta origem: " + contaOrigem.conta + ", Conta destino: " + contaDestino.conta + ", Valor: "
				+ valor + ", Data e hora: " + data;
	}

}
