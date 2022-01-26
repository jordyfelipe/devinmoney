package devinmoney.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

	protected String nome;
	protected Integer cpf;
	protected Double rendaMensal;
	protected Integer conta;
	protected Agencia agencia;
	protected Double saldo;

	protected Integer senha;
	protected String extrato = "Extrato: Data | Hora | Descricao | Valor:\n";
	protected List<Transacao> transacoes = new ArrayList<Transacao>();

	public Conta(String nome, Integer cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.rendaMensal = rendaMensal;
		this.conta = conta;
		this.agencia = agencia;
		this.saldo = saldo;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Double getRendaMensal() {
		return rendaMensal;
	}

	public void setRendaMensal(Double rendaMensal) {
		this.rendaMensal = rendaMensal;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Integer getSenha() {
		return senha;
	}

	public void setSenha(Integer senha) {
		this.senha = senha;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getExtrato() {
		return extrato;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void deposito(Double valor) {
		this.saldo += valor;
		this.setExtrato(DescricaoExtrato.DEPOSITO, valor);
	}

	public void alterarDadosCadastrais(String nome, Double renda, Integer senha) {
		this.setNome(nome);
		this.setRendaMensal(renda);
		this.setSenha(senha);
	}

	public void setExtrato(DescricaoExtrato descricao, Double valor) {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String agoraFormatado = agora.format(formatador);
		this.extrato += agoraFormatado + " " + descricao + " R$" + valor + "\n";
	}

	public abstract void saque();

	public abstract void transferir(Conta contaDestino, Double valor);

	@Override
	public String toString() {
		return "Cliente: " + nome + ", CPF: " + cpf + ", Renda mensal: " + rendaMensal + ", Número conta: " + conta
				+ ", Agência: " + agencia + ", Saldo atual: " + saldo;
	}

}
