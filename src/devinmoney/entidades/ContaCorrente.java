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
			} else {
				System.out.println("Não foi possível realizar a operação " + this.nome
						+ ", o valor excede o limite máximo do cheque especial.");
			}
		}
	}

	@Override
	public void transferir(Conta contaDestino, Double valor) {
		if (this.saldo >= valor) {
			this.saldo -= valor;
			contaDestino.setSaldo(contaDestino.getSaldo() + valor);
			this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
			contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
		} else {
			Double valorFaltante = valor - this.saldo;
			if (this.limiteChequeEspecial >= valorFaltante) {
				this.saldo = -valorFaltante;
				this.limiteChequeEspecial -= valorFaltante;
				contaDestino.setSaldo(contaDestino.getSaldo() + valor);
				this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
				contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
			} else {
				System.out.println("Não foi possível realizar a operação " + this.nome
						+ ", o valor excede o limite máximo do cheque especial.");
			}
		}
	}

}
