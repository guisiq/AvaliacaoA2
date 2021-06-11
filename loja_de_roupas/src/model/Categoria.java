package model;

public enum Categoria {
		CALCA("calça",1),
		CAMISA("camisa",2),
		SHORT("short",3),
		VESTIDO("vestido",4);
		private String label;
		private int indice;
		Categoria(String label,int indice){
			this.label= label;
			this.indice = indice;
		}
		public String getLabel() {
			return label;
		}
		
		public int getIndice() {
			return indice;
		}
		
}
