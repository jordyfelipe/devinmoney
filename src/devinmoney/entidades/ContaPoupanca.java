package devinmoney.entidades;

import devinmoney.exceptions.EntradaIncorretaException;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(String nome, String cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha) throws EntradaIncorretaException {
		super(nome, cpf, rendaMensal, conta, agencia, saldo, senha);
	}

	public String simularRendimento(Integer periodo, Double taxa) {
		Double montante = 0.00;
		// Converte taxa anual em mensal
		taxa = (Math.pow(1.0 + (taxa / 100), 1.0 / 12.0) - 1);
		// formata para 4 casas depois da vírgula
		taxa = formatarCasas(taxa, 4);
		for (int mes = 1; mes <= periodo; ++mes) {
			// calcula novo montante durante mês especificado
			montante = this.saldo * Math.pow(1.0 + taxa, mes);
		}
		return String.format("%.2f", montante);

	}

	private Double formatarCasas(Double valor, int qtdCasas) {
		Double fator = Math.pow(10, qtdCasas);
		return (Math.floor(valor * fator) / fator);
	}

	@Override
	public String toString() {
		return "Conta Poupanca: Nome: " + nome + ", CPF: " + cpf + ", Renda Mensal: " + rendaMensal + ", Conta: "
				+ conta + ", Agência: " + agencia.getDescricao() + ", Saldo: " + saldo;
	}

}
