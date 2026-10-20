package compilador.lexico;

public class Lexer {
    private String entrada;
    private int pos;
    private char caracterActual;

    public Lexer(String entrada) {
        this.entrada = entrada;
        this.pos = 0;
        this.caracterActual = entrada.length() > 0 ? entrada.charAt(pos) : '\0';
    }

    private void avanzar() {
        pos++;
        caracterActual = (pos < entrada.length()) ? entrada.charAt(pos) : '\0';
    }

    public Token siguienteToken() {
        while (caracterActual != '\0') {
            if (Character.isWhitespace(caracterActual)) {
                avanzar();
                continue;
            }
            if (Character.isDigit(caracterActual)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(caracterActual)) {
                    sb.append(caracterActual);
                    avanzar();
                }
                return new Token(sb.toString(), Token.TipoToken.INT_LITERAL);
            }
            if (Character.isLetter(caracterActual)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isLetterOrDigit(caracterActual) || caracterActual == '_') {
                    sb.append(caracterActual);
                    avanzar();
                }
                String id = sb.toString();
                if (id.equals("int")) return new Token(id, Token.TipoToken.INT);
                if (id.equals("float")) return new Token(id, Token.TipoToken.FLOAT);
                if (id.equals("string")) return new Token(id, Token.TipoToken.STRING);
                return new Token(id, Token.TipoToken.ID);
            }
            switch (caracterActual) {
                case '+': avanzar(); return new Token("+", Token.TipoToken.PLUS);
                case '-': avanzar(); return new Token("-", Token.TipoToken.MINUS);
                case '*': avanzar(); return new Token("*", Token.TipoToken.MUL);
                case '/': avanzar(); return new Token("/", Token.TipoToken.DIV);
                case '=': avanzar(); return new Token("=", Token.TipoToken.EQUAL);
                case '<': avanzar(); return new Token("<", Token.TipoToken.LESS_THAN);
                case ';': avanzar(); return new Token(";", Token.TipoToken.SEMICOLON);
                case '(': avanzar(); return new Token("(", Token.TipoToken.LPARENT);
                case ')': avanzar(); return new Token(")", Token.TipoToken.RPARENT);
                default:
                    throw new RuntimeException("Carácter no reconocido: " + caracterActual);
            }
        }
        return new Token("", Token.TipoToken.EOF);
    }
}
