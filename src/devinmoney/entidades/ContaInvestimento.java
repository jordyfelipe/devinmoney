package devinmoney.entidades;

import devinmoney.exceptions.EntradaIncorretaException;

public class ContaInvestimento extends Conta {

	public ContaInvestimento(String nome, String cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha, TipoInvestimento tipoInvestimento) throws EntradaIncorretaException {
		super(nome, cpf, rendaMensal, conta, agencia, saldo, senha);
		this.tipoInvestimento = tipoInvestimento;
	}

	TipoInvestimento tipoInvestimento;

	public String simularRendimento(Integer periodo) {
		Double montante = 0.00;
		Double taxa = 0.00;
		// Converte taxa anual em mensal
		taxa = (Math.pow(1.0 + (this.tipoInvestimento.getTaxa() / 100), 1.0 / 12.0) - 1);
		// formata para 4 casas depois da v?rgula
		taxa = formatarCasas(taxa, 4);
		for (int mes = 1; mes <= periodo; ++mes) {
			// calcula novo montante durante m?s especificado
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
		return "Conta Investimento: " + "Nome: " + nome + ", CPF: " + cpf + ", Renda Mensal: " + rendaMensal
				+ ", Conta: " + conta + ", Ag?ncia: " + agencia.getDescricao() + ", Saldo: " + saldo
				+ ", tipoInvestimento: " + tipoInvestimento;
	}

}
