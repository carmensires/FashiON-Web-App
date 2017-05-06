package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;

import dl.NotificacionCompleta;
import bl.Ejb;

@ManagedBean
@GraphicImageBean
public class ImageBean {
	@Inject
	@EJB
	Ejb ejb = new Ejb();

	public byte[] getProfileImage(int idUser) {
		return ejb.getProfileImage(idUser);
	}

	public byte[] getPublicacionImage(int idPublicacion) {
		byte[] image = ejb.getPublicacionImage(idPublicacion);
		return image;
	}

	public byte[] getNotificacionImage(NotificacionCompleta notificacion) {
		byte[] image;
		if (notificacion.isPub()) {
			image = ejb.getPublicacionImage(notificacion.getNotificacion()
					.getPublicacionBean().getIdPublicacion());
		} else {
			image = null;
		}
		return image;
	}

}
