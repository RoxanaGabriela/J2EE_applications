package ro.pub.cs.paj.discussionforum.graphicuserinterfaces;

import java.io.PrintWriter;
import java.util.List;

import ro.pub.cs.paj.discussionforum.businesslogic.EntityManager;
import ro.pub.cs.paj.discussionforum.db.Client;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;

public class AdministratorPostGraphicUserInterface {

	private static EntityManager entityManager = new EntityManager();

	public AdministratorPostGraphicUserInterface() {
	}

	public static void displayAdministratorGraphicUserInterface(String username, List<Post> posts,
			List<Comment> comments, int currentRecordsPerPage, int currentPage, PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");		
		content.append("<html>\n");
		content.append("	<head>\n");
		content.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n");
		content.append("        <title>" + "DiscussionForum" + "</title>\n");		
		content.append("		<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />\n");
		content.append("		<link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n");
		content.append("	</head>\n");

		content.append("	<body>\n");
		content.append("		<h2 style=\"text-align: center\">" + Constants.APPLICATION_NAME.toUpperCase() + "</h2>\n");
		content.append("		<form action=\"" + Constants.ADMINISTRATOR_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.ADMINISTRATOR_FORM + "\">\n");
		content.append("			<table>\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		content.append("							<input type=\"image\" name=\"" + Constants.HOME.toLowerCase() + "\" value=\"" + Constants.HOME + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("						</td>\n");
		content.append("					<td>\n");
		content.append("						<input type=\"image\" name=\"" + Constants.SIGNOUT.toLowerCase() + "\" value=\"" + Constants.SIGNOUT + "\" src=\"./images/user_interface/signout.png\" />\n");
		content.append("					</td>\n");
		content.append("					</tr>\n");
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("			<p style=\"text-align:right\">\n");
		content.append("			" + Constants.WELCOME_MESSAGE + username + "\n");
		content.append("			<br/>\n");
		content.append("				" + Constants.RECORDS_PER_PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.POST_FORM + ".submit()\">\n");
		for (int recordsPerPageValue : Constants.RECORDS_PER_PAGE_VALUES) {
			content.append("				<option value=\"" + recordsPerPageValue + "\"" + ((recordsPerPageValue == currentRecordsPerPage) ? " SELECTED" : "") + ">" + recordsPerPageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("				" + Constants.PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.POST_FORM + ".submit()\">\n");
		for (int pageValue = 1; pageValue <= posts.size() / currentRecordsPerPage + ((posts.size() % currentRecordsPerPage) != 0 ? 1 : 0); pageValue++) {
			content.append("				<option value=\"" + pageValue + "\"" + ((pageValue == currentPage) ? " SELECTED" : "") + ">" + pageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("			</p>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		for (int index = 0; index < posts.size(); index++) {
			if (index + 1 < ((currentPage - 1) * currentRecordsPerPage + 1)
					|| index + 1 > (currentPage * currentRecordsPerPage)) {
				continue;
			}
			content.append("					<div id=\"wrapperrelative\">\n");
			content.append("						<div id=\"wrappertop\"></div>\n");
			content.append("						<div id=\"wrappermiddle\">\n");
			content.append("							<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("								<tbody>\n");
			content.append("									<tr>\n");
			content.append("										<td>\n");
			content.append("											<table>\n");
			content.append("												<tbody>\n");
			content.append("													<tr>\n");
			content.append("														<td style=\"text-align: center;\">" + posts.get(index).getClientUsername() + "</td>\n");
			content.append("													</tr>\n");
			content.append("													<tr>\n");
			content.append("														<td style=\"text-align: center;\">" + posts.get(index).getTitle() + "</td>\n");
			content.append("													</tr>\n");
			content.append("													<tr>\n");
			content.append("														<td style=\"text-align: center;\">" + posts.get(index).getDescription() + "</td>\n");
			content.append("													</tr>\n");
			content.append("													<tr>\n");
			content.append("														<td>\n");
			content.append("															<input type=\"image\" height=32 width=32 name=\"" + Constants.VIEW_BUTTON_NAME.toLowerCase() + "_" + posts.get(index).getId() + "\" value=\"" + Constants.VIEW_BUTTON_NAME + "\" src=\"./images/user_interface/right-arrow-icon.png\" />\n");
			content.append("														</td>\n");
			content.append("													</tr>\n");
			content.append("												</tbody>\n");
			content.append("											</table>\n");
			content.append("										</td>\n");
			if (posts.get(index).isBanned() == 0) {
				content.append("									<td>\n");
				content.append("										<table>\n");
				content.append("											<tbody>\n");
				content.append("												<tr>\n");
				content.append("													<td>\n");
				content.append("														<input type=\"image\" height=32 width=32 name=\"" + Constants.ACCEPT_BUTTON_NAME.toLowerCase() + "_" + posts.get(index).getId() + "\" value=\"" + Constants.ACCEPT_BUTTON_NAME + "\" src=\"./images/user_interface/green-accept-button.jpg\" />\n");
				content.append("													</td>\n");
				content.append("												</tr>\n");
				content.append("												<tr>\n");
				content.append("													<td>\n");
				content.append("														<input type=\"image\" height=32 width=32 name=\"" + Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + posts.get(index).getId() + "\" value=\"" + Constants.DELETE_BUTTON_NAME + "\" src=\"./images/user_interface/red-delete-button.jpg\" />\n");
				content.append("													</td>\n");
				content.append("												</tr>\n");
				content.append("											</tbody>\n");
				content.append("										</table>\n");
				content.append("									</td>\n");
			}
			content.append("									</tr>\n");
			content.append("								</tbody>\n");
			content.append("							</table>\n");
			content.append("						</div>\n");
			content.append("						<div id=\"wrapperbottom\"></div>\n");
			content.append("					</div>\n");
		}
		content.append("						</td>\n");
		content.append("					</tr>\n");
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("		</form>");
		content.append("	</body>\n");
		content.append("</html>");
		printWriter.println(content.toString());
	}
}
