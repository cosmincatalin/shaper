package com.cosminsanda.shaper.parsing

import com.cosminsanda.shaper.ast.Shape
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import java.io.InputStream

data class AntlrParsingResult(val root : ShaperParser.ShapeContext?) {}

data class ParsingResult(val root : Shape?) {}

object ShaperAntlrParserFacade {

    fun parse(inputStream: InputStream) : AntlrParsingResult {
        val lexer = ShaperLexer(ANTLRInputStream(inputStream) as CharStream)
        val parser = ShaperParser(CommonTokenStream(lexer) as TokenStream)
        val antlrRoot = parser.shape()
        return AntlrParsingResult(antlrRoot)
    }

}

object ShaperParserFacade {

    fun parse(inputStream: InputStream) : ParsingResult {
        val antlrParsingResult = ShaperAntlrParserFacade.parse(inputStream)
        val antlrRoot = antlrParsingResult.root
        val astRoot = antlrRoot?.toAst()
        return ParsingResult(astRoot)
    }

}