package ro.pub.cs.paj.discussionforum.graphicuserinterfaces;

import java.io.PrintWriter;
import java.util.List;

import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Topic;

public class TopicDiscussionGraphicInterface {
	
	public TopicDiscussionGraphicInterface(){
		
	}
	
	public static void displayTopicDiscussionGraphicUserInterface(boolean isLogedIn, List<Comment> comments,PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");		
		content.append("<html>\n");
		content.append("<head>\n");
		content.append("        <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n");		 						
		content.append("        <title>" + "DiscussionForum" + "</title>\n");		
		content.append("    </head>\n");
		content.append("    <body>\n");
		
		content.append("        <form action=\"PostDiscussionServlet\" method=\"post\">\n");
		
		content.append("<br>\n");
		content.append("DiscussionForum\n");
		content.append("<br>\n");
		content.append("<br>\n");
		
		content.append("<table name=\"ListTopics\" style=\"text-align: left; width: 100%;\" border=\"1\" cellpadding=\"2\" cellspacing=\"2\">\n");				
		content.append(" <tbody>\n");
		content.append(" <tr>\n");
		
		for(int i = 0;i < comments.size(); i++){
			content.append("<tr>\n");
			content.append("<th>\n");
			//content.append(topics.get(i).getTitle() + "\n");
			
			content.append(comments.get(i).getDescription());
								
			content.append("</th>\n");
			content.append("</tr>\n");
			
		}
		
		
		content.append(" </tr>\n");
		content.append(" </tbody>\n");
		content.append(" </table>\n");  		
		content.append("<br>\n");
		
		if (isLogedIn) {					

			content.append("<input type=\"text\" name=\"NewTopicComment\" value=\"new topic comment\" style=\" width: 1023px; height: 55px;\"  onclick=this.value=''>");
			content.append("<br>\n");
			content.append("<input type=\"submit\" name=\"NewComment\" value=\"New Comment\"/>");
		}
	
				
		content.append("<br>\n");
		content.append("</body>\n");
		content.append("</html>");
		
		printWriter.println(content.toString());
		
	}
}
