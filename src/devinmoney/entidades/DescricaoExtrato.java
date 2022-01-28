package devinmoney.entidades;

public enum DescricaoExtrato {

	DEPOSITO("Depósito recebido"), SAQUE("Saque efetuado"), TRANSFERENCIA_EFETUADA("Transferência efetuada"),
	TRANSFERENCIA_RECEBIDA("Transferência recebida"), RESGATE("Resgate"),
	APLICACAO("Aplicação");

	private String tipo;

	private DescricaoExtrato(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}
