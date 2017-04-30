package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import bl.Ejb;
import dl.Comentario;

@ManagedBean
@RequestScoped
public class ComentarioBean {

	@EJB
	Ejb ejb=new Ejb();
	
	private Comentario comentario = new Comentario();

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}
	
	public void addComentario(int idPublicacion){
	
		ejb.addComentario(comentario,idPublicacion);
		ejb.addNotificacionComentario();
		comentario=new Comentario();

	}
	
	public void removeComentario(int idComentario){
		ejb.removeComentario(idComentario);
	}
}