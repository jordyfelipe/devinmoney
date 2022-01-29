package devinmoney.entidades;

import devinmoney.exceptions.EntradaIncorretaException;

public class ContaCorrente extends Conta {

	private Double limiteChequeEspecial;

	public ContaCorrente(String nome, String cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha) throws EntradaIncorretaException {
		super(nome, cpf, rendaMensal, conta, agencia, saldo, senha);
		limiteChequeEspecial = calcularLimiteChequeEspecial(rendaMensal);
	}

	public Double exibirSaldoAtualLimiteChequeEspecial() {
		return this.limiteChequeEspecial;
	}

	// Limite cheque especial não pode exceder 30% da renda mensal.
	private Double calcularLimiteChequeEspecial(Double rendaMensal) {
		return rendaMensal * 0.3;
	}

	public Double getLimiteChequeEspecial() {
		return limiteChequeEspecial;
	}

	@Override
	public void sacar(Double valor) {
		if (this.saldo >= valor) {
			this.saldo -= valor;
			this.setExtrato(DescricaoExtrato.SAQUE, valor);
		} else {
			Double valorFaltante = valor - this.saldo;
			if (this.limiteChequeEspecial >= valorFaltante) {
				this.saldo = -valorFaltante;
				this.limiteChequeEspecial -= valorFaltante;
				this.setExtrato(DescricaoExtrato.SAQUE, valor);
				System.out.println("Saque efetuado com sucesso!");
			} else {
				System.out.println("Não foi possível realizar a operação " + this.nome
						+ ", o valor excede o limite máximo do cheque especial.");
			}
		}
	}

	@Override
	public void transferir(Conta contaDestino, Double valor) {
		if (contaDestino.conta != this.conta) {
			if (this.saldo >= valor) {
				this.saldo -= valor;
				contaDestino.setSaldo(contaDestino.getSaldo() + valor);
				this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
				contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
				System.out.println("Transferência efetuada com sucesso!");
			} else {
				Double valorFaltante = valor - this.saldo;
				if (this.limiteChequeEspecial >= valorFaltante) {
					this.saldo = -valorFaltante;
					this.limiteChequeEspecial -= valorFaltante;
					contaDestino.setSaldo(contaDestino.getSaldo() + valor);
					this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
					contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
					System.out.println("Transferência efetuada com sucesso!");
				} else {
					System.out.println("Não foi possível realizar a operação " + this.nome
							+ ", o valor excede o limite máximo do cheque especial.");
				}
			}
		} else {
			System.out.println(
					"Não foi possível realizar a transação, número de conta destino precisa ser diferente do numero da conta origem.");
		}
	}

	@Override
	public void alterarDadosCadastrais(String nome, Double renda, Integer senha) {
		this.setNome(nome);
		this.setRendaMensal(renda);
		this.setSenha(senha);
		this.limiteChequeEspecial = calcularLimiteChequeEspecial(renda);
		System.out.println("Dados cadastrais alterados com sucesso!");
	}

	@Override
	public String toString() {
		return "Conta Corrente: Nome: " + nome + ", CPF: " + cpf + ", Renda Mensal: " + rendaMensal + ", Conta: "
				+ conta + ", Agência: " + agencia.getDescricao() + ", Saldo: " + saldo + ", Limite cheque especial: "
				+ limiteChequeEspecial;
	}

}
