package com.cosminsanda.shaper.parsing

import com.cosminsanda.shaper.ShaperLexer
import com.cosminsanda.shaper.ShaperParser
import com.cosminsanda.shaper.ast.Shaper
import com.cosminsanda.shaper.ast.toAst
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import java.io.InputStream

data class AntlrParsingResult(val root : ShaperParser.ShaperContext)

data class ParsingResult(val root : Shaper)

object ShaperAntlrParserFacade {

    fun parse(inputStream: InputStream) : AntlrParsingResult {
        val lexer = ShaperLexer(CharStreams.fromStream(inputStream))
        val parser = ShaperParser(CommonTokenStream(lexer) as TokenStream)
        val antlrRoot = parser.shaper()
        return AntlrParsingResult(antlrRoot)
    }

}

object ShaperParserFacade {

    fun parse(inputStream: InputStream) : ParsingResult {
        val antlrParsingResult = ShaperAntlrParserFacade.parse(inputStream)
        val antlrRoot = antlrParsingResult.root
        val astRoot = antlrRoot.toAst()
        return ParsingResult(astRoot)
    }

}