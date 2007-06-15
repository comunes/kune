package org.ourproject.kune.server;

import java.util.Date;

import org.ourproject.kune.client.rpc.KuneDocumentService;
import org.ourproject.kune.client.rpc.dto.KuneDoc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class KuneDocumentServiceServlet extends RemoteServiceServlet implements
		KuneDocumentService {
	private static final long serialVersionUID = 1L;
	private static final String content = "<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Morbi aliquam turpis rhoncus sapien volutpat condimentum. Vestibulum dignissim, risus et ullamcorper sollicitudin, risus mi molestie lectus, ut aliquam nulla dolor eu nisl. Duis volutpat. Sed eget lectus lacinia lacus interdum facilisis. Aliquam tincidunt sem at mi. Duis a ipsum vel turpis volutpat adipiscing. Sed at libero sit amet lacus elementum tempus. Vestibulum sit amet tellus. Duis dolor. Praesent convallis lorem ac metus. Curabitur malesuada pede id dui. Vivamus tincidunt risus vehicula enim. Nulla fermentum. Sed placerat lacus eget erat. Proin dolor enim, aliquam ut, vehicula sit amet, blandit non, arcu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n</p><p>\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Duis sapien. Suspendisse potenti. Sed imperdiet pulvinar tortor. Ut vel nisi. Nam commodo, mauris vitae congue placerat, mauris eros vulputate odio, ac facilisis erat quam at enim. Cras iaculis pede sit amet dui. Cras arcu. Fusce non orci vitae lacus hendrerit auctor. Aliquam leo.\n</p><p>\nVestibulum orci dolor, hendrerit et, dapibus vel, congue ac, velit. Maecenas est. Nam in velit eget ante consequat vulputate. Nam posuere. Nunc lectus. Vestibulum facilisis. Aliquam elit nunc, facilisis eget, bibendum at, dignissim at, nulla. Sed ullamcorper, mi a eleifend tincidunt, metus tortor ultricies mi, in tempor arcu tellus nec erat. Quisque semper, turpis in gravida suscipit, elit leo sollicitudin risus, vel laoreet velit mi a massa. Aliquam non nulla a sapien dapibus bibendum. Sed auctor neque vel justo. Etiam cursus. Nunc eget lectus. In euismod urna vitae dui luctus consequat. Nunc cursus vulputate erat. Duis vel justo vel ante imperdiet rutrum. Curabitur eget turpis ac pede interdum accumsan. Ut velit.\n</p><p>\nProin vitae eros ut pede lacinia aliquam. Praesent in metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean sed eros. Phasellus id risus. Vivamus non nunc eget purus feugiat sagittis. Mauris id tortor ut lectus mollis porttitor. Fusce lobortis leo quis augue suscipit tincidunt. Ut tristique, nunc at egestas blandit, sem leo tincidunt nibh, id tempor neque elit vitae tortor. Nulla sapien est, suscipit sed, aliquam eget, viverra suscipit, justo.\n</p>";

	public KuneDoc getRootDocument(String projectName) {
		String head = "<h1>Server content generated at: " + new Date() + "</h1>";
		return new KuneDoc("name", head + content, "license");
	}
	
	public void setRootDocument(String projectName, KuneDoc doc) {
		System.err.println("la-la-la-laaaaaa la-la-laaaaaaa la-la-laaaaaaa");
	}
}
