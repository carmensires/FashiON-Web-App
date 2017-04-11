package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import bl.Ejb;
import dl.Like;

@ManagedBean
@RequestScoped
public class LikeBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Like like = new Like();

	public Like getLike() {
		return like;
	}

	public void setLike(Like like) {
		this.like = like;
	}
	
	public void addLike(int idPublicacion){
		
		ejb.addLike(idPublicacion);
		ejb.addNotificacionLike(idPublicacion);
	}
	
	public int getLikes(int idPublicacion){
		return ejb.getLikes(idPublicacion);
	}
	
	public boolean getLiked(int idPublicacion){
		return ejb.getLiked(idPublicacion);
	}
	
	public boolean getDisliked(int idPublicacion){
		boolean liked=true;
		if(ejb.getLiked(idPublicacion)==true)
			liked=false;
		
		return liked;
	}
	public void removeLike(int idPublicacion){
		ejb.removeLike(idPublicacion);
		ejb.removeNotificacionLike(idPublicacion);
	}
	
	public void darLike(int idPublicacion)
	{
		if(this.getDisliked(idPublicacion))
		{
			this.removeLike(idPublicacion);
		}
		else if(this.getLiked(idPublicacion))
		{
			this.addLike(idPublicacion);
		}
	}

}
