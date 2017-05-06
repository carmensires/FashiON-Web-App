package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dl.Comentario;
import dl.Valoracion;
import bl.Ejb;

@ManagedBean
@RequestScoped
public class ValoracionBean {

	@EJB
	Ejb ejb = new Ejb();

	private Valoracion valoracion = new Valoracion();

	public Valoracion getValoracion() {
		return valoracion;
	}

	public void setValoracion(Valoracion valoracion) {
		this.valoracion = valoracion;
	}

	public void valorar(int puntuacion, Comentario comentario) {
		this.valoracion.setPuntuacion(puntuacion);
		this.valoracion.setComentarioBean(comentario);
		ejb.valorar(valoracion);
		ejb.addNotificacionValoracion(valoracion);
	}

	public String estrella(int n, Comentario comentario) {
		String foto;
		int puntuacion = ejb.getValoracion(comentario);
		if (puntuacion < n) {
			foto = "fotos/starempty.png";
		} else
			foto = "fotos/star.png";
		return foto;
	}

	public String getStringValoracion(int idComentario, int idUser) {
		int puntuacion = ejb.getPuntuacion(idComentario, idUser);
		return "Valoracion: " + puntuacion + ". Ver Comentario";
	}

}
