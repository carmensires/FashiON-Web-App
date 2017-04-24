package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;

import bl.Ejb;



@ManagedBean
@GraphicImageBean
public class ImageBean {
	@Inject
	
	@EJB
	Ejb ejb=new Ejb();
	

	public byte[] getProfileImage(int idUser)
	{
		return ejb.getProfileImage(idUser);
	}
	
	public byte[] getPublicacionImage(int idPublicacion)
	{
		return ejb.getPublicacionImage(idPublicacion);
	}
	

}
