package PrintAst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class printAST {
	
		public static void main(String args[]) throws IOException{
			
			/**Get the FilePath and get the content of the java file*/
			String FilePath = "C:/Users/User/workspace/FYP/src/PrintAst/SimpleProgram.java";
			String source = getContent(FilePath);
			
			/**Parse the source file and get the AST*/
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(source.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			
			cu.accept(new ASTVisitor() {
				
				/*Use the stack to determine the level of the tree*/
				StringBuilder path = new StringBuilder();
				Stack <String> st = new Stack <String>();
				
				public void preVisit(ASTNode ast){
					
					String value = ast.getClass().getSimpleName();
					st.push(value);
					int length = st.size();
					path.append(length + " ");
					for (int i = 1; i < length; i++){
						path.append("   ");
					}
					
					System.out.println();
					System.out.print(path.toString() + " " + value);
					String valueContain = ast.toString();
					valueContain = valueContain.trim().replaceAll("\n", "");
					System.out.print(" : " + valueContain);
					
					path.setLength(0);
					//st.pop();
					
				}
				
				public void postVisit(ASTNode ast){
					
					//String value = ast.getClass().getSimpleName();
					//st.push(value);
					st.pop();
				}
			});
			
		}
		
		/**Retrieve the content of the Java File*/
		private static String getContent(String FilePath) throws IOException {
		    
			 StringBuilder fileData = new StringBuilder(1000);
			 BufferedReader reader = new BufferedReader(new FileReader(FilePath));

			 char[] buf = new char[10];
			 int numRead = 0;
			
			 while ((numRead = reader.read(buf)) != -1) {
				 String readData = String.valueOf(buf, 0, numRead);
				 fileData.append(readData);
				 buf = new char[1024];
			 }

			 reader.close();
			 return  fileData.toString();
			
		 }
}

