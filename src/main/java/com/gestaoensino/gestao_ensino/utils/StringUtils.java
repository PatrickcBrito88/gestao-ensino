package com.gestaoensino.gestao_ensino.utils;

import org.springframework.lang.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.apache.commons.lang3.StringUtils.replaceIgnoreCase;

@SuppressWarnings("java:S2176")
public final class StringUtils extends org.springframework.util.StringUtils {
    private StringUtils() {
    }

    /**
     * Retira os primeiros N caracteres de uma string
     *
     * @param input  String a ser recortada.
     * @param number o número de caracteres finais desejados.
     * @return a String sem os primeiros N caracteres
     */
    public static String getLastCharacters(String input, Integer number) {
        String lastChars;
        if (input.length() > number) {
            lastChars = input.substring(input.length() - number);
        } else {
            lastChars = input;
        }
        return lastChars;
    }

    /**
     * Retira os últimos N caracteres de uma String
     *
     * @param input  String a ser recortada
     * @param number o número de caracteres finais a serem recortados
     * @return a String sem os últimos N caracteres
     */
    public static String getFirstCharacters(String input, Integer number) {
        String firstChars;
        if (input.length() > number) {
            firstChars = input.substring(0, input.length() - number);
        } else {
            firstChars = input;
        }
        return firstChars;
    }

    /**
     * Recupera os textos presentes no arquivo .properties passado como parâmetro
     *
     * @param nomeArquivoMensagens o nome do arquivo de mensagems
     * @param chaveMensagem        a chave da mensagem
     * @param params               os parâmetros
     * @return String a mensagem com os parâmetros interpolados
     */
    public static String getMensagem(final String nomeArquivoMensagens, final String chaveMensagem,
                                     final Object... params) {

        ResourceBundle bundle = ResourceBundle.getBundle(nomeArquivoMensagens, Locale.getDefault());
        String pattern = bundle.getString(chaveMensagem);
        return MessageFormat.format(pattern, params);
    }

    /**
     * Recupera o texto na chaveMensagem presente no arquivo ValidationMessages.properties passada como parâmetro
     * <p>
     * Ver também {@link StringUtils#getMensagem(String, String, Object...)}
     */
    public static String getMensagemValidacao(final String chaveMensagem, final Object... params) {

        ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", Locale.getDefault());
        String pattern = bundle.getString(chaveMensagem);
        return MessageFormat.format(pattern, params);
    }

    /**
     * Interpola as variáveis de um Map na string fornecida.
     * O método é case insensitive quanto ao nome da chave e variável
     *
     * @param source       A string fornecida
     * @param mappedValues o Map
     * @param hasCifrao    se a variável está com notação de cifrão ou não
     * @return a String interpolada
     * <p>
     * Exemplo 1:
     * source -> "{variavel1} o dia está muito {variavel2}"
     * mappedValues -> {
     * "variavel1": "Hoje"
     * "variavel2": "bonito!"
     * }
     * hasCifrao -> false
     * interpolate(source, mappedValues, hasCifrao) -> "Hoje o dia está muito bonito!"
     * <p>
     * Exemplo 2:
     * source -> "${VARIAVEL1} o dia está muito ${vaRIavel2}"
     * mappedValues -> {
     * "VaRIavel1": "Hoje"
     * "variaVEl2": "bonito!"
     * }
     * hasCifrao -> true
     * interpolate(source, mappedValues, hasCifrao) -> "Hoje o dia está muito bonito!"
     */
    public static String interpolate(final String source, Map<String, ?> mappedValues, boolean hasCifrao) {
        final String cifrao = hasCifrao ? "$" : "";
        String sourceStr = source;
        for (Map.Entry<String, ?> entrySet : mappedValues.entrySet()) {
            Object value = entrySet.getValue();
            if (value != null)
                sourceStr = replaceIgnoreCase(sourceStr, cifrao + "{" + entrySet.getKey() + "}", value.toString());
        }
        return sourceStr;
    }

    /**
     * Verifica se uma string não é nem nula nem vazia
     *
     * @param string a string
     * @return true se a string for nula ou vazia
     */
    public static boolean isNotNullOrEmpty(@Nullable String string) {
        return hasLength(string);
    }

    /**
     * Retorna uma string vazia se a string for nula
     *
     * @param str a string vazia
     * @return retorna null se a string for vazia, a própria string caso contrário
     */
    public static String nullToEmptyStr(@Nullable String str) {
        if (str == null) return "";
        return str;
    }

    /**
     * Retorna uma string vazia se o objeto for nulo
     * Ver {@link StringUtils#nullToEmptyStr(String)}
     */
    public static String nullToEmptyStr(@Nullable Object obj) {
        if (obj == null) return "";
        return obj.toString();
    }
}
