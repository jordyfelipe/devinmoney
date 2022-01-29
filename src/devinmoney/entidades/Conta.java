package devinmoney.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import devinmoney.exceptions.EntradaIncorretaException;

public abstract class Conta {

	protected String nome;
	protected String cpf;
	protected Double rendaMensal;
	protected Integer conta;
	protected Agencia agencia;
	protected Double saldo;

	protected Integer senha;
	protected String extrato = "Abaixo extrato:\n";

	public Conta(String nome, String cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha) throws EntradaIncorretaException {
		this.nome = nome;
		this.cpf = setCpf(cpf);
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

	public String getCpf() {
		return cpf;
	}

	private String validarCpf(String cpf) throws EntradaIncorretaException {
		Pattern pattern = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}");
		Matcher matcher = pattern.matcher(cpf);
		if (matcher.matches()) {
			return cpf;
		} else {
			throw new EntradaIncorretaException("CPF inválido!");
		}
	}

	public String setCpf(String cpf) throws EntradaIncorretaException {
		return validarCpf(cpf);
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
		return extrato + "\nSaldo atual: " + saldo;
	}

	public void depositar(Double valor) {
		this.saldo += valor;
		this.setExtrato(DescricaoExtrato.DEPOSITO, valor);
		System.out.println("Depósito efetuado com sucesso!");
	}

	public void alterarDadosCadastrais(String nome, Double renda, Integer senha) {
		this.setNome(nome);
		this.setRendaMensal(renda);
		this.setSenha(senha);
		System.out.println("Dados cadastrais alterados com sucesso!");
	}

	/**
	 * Deve ser utilizado para incluir uma linha no extrato de operações que não
	 * possuam conta destino de titularidade diferente.
	 * 
	 * @param descricao
	 * @param valor
	 */
	public void setExtrato(DescricaoExtrato descricaoExtrato, Double valor) {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String agoraFormatado = agora.format(formatador);
		this.extrato += agoraFormatado + " " + descricaoExtrato.getTipo() + " R$" + valor + "\n";
	}

	/**
	 * Deve ser utilizado para incluir uma linha no extrato de operações que possuam
	 * conta de origem ou destino.
	 * 
	 * @param descricao
	 * @param valor
	 * @param titularConta destino ou origem
	 */
	public void setExtrato(DescricaoExtrato descricaoExtrato, Double valor, String titularConta) {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String agoraFormatado = agora.format(formatador);
		this.extrato += agoraFormatado + " " + descricaoExtrato.getTipo() + " R$" + valor + " " + titularConta + "\n";
	}

	public void sacar(Double valor) {
		if (this.saldo >= valor) {
			this.saldo -= valor;
			this.setExtrato(DescricaoExtrato.SAQUE, valor);
			System.out.println("Saque efetuado com sucesso!");
		} else {
			System.out.println("Não foi possível realizar a operação, saldo insuficiente.");
		}
	}

	public void transferir(Conta contaDestino, Double valor) {
		if (contaDestino.conta != this.conta) {
			if (this.saldo >= valor) {
				this.saldo -= valor;
				contaDestino.setSaldo(contaDestino.getSaldo() + valor);
				this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
				contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
				System.out.println("Depósito efetuado com sucesso!");
			} else {
				System.out.println("Não foi possível realizar a operação, saldo insuficiente.");
			}
		} else {
			System.out.println(
					"Não foi possível realizar a transação, número de conta destino precisa ser diferente do numero da conta origem.");
		}
	}

}
