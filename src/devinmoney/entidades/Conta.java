package devinmoney.entidades;

public abstract class Conta {

	protected String nome;
	protected Integer cpf;
	protected Double rendaMensal;
	protected Integer conta;
	protected Agencia agencia;
	protected Double saldo;
	
	protected Integer senha;
	protected String extrato;

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

	public abstract void saque();

	public abstract void deposito();
	
	public abstract void setExtrato();
	
	public abstract void transferir(Conta contaDestino, Double valor);
	
}
