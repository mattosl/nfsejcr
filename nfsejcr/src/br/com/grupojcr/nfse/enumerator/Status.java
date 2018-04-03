package br.com.grupojcr.nfse.enumerator;

public enum Status {
	PENDENTE(0, "Pendente"),
	EXPORTADO(1, "Exportado"),
	REMOVIDO(2, "Removido");
	
	private Integer id;
	private String descricao;
	
	private Status(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Status obterPorCodigo(Integer codigo) {
		if(codigo != null) {
			for(Status status : Status.values()) {
				if(status.getId().equals(codigo)) {
					return status;
				}
			}
		}
		return null;
	}

}
