package br.com.grupojcr.nfse.enumerator;

public enum EstiloXML {
	CURITIBA_UNICA(0),
	CURITIBA_VARIAS(1);
	
	private Integer id;
	
	private EstiloXML(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
