package com.cosminsanda.shaper.parsing

import com.cosminsanda.shaper.ShaperLexer
import com.cosminsanda.shaper.ShaperParser
import com.cosminsanda.shaper.ast.Shaper
import com.cosminsanda.shaper.ast.toAst
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import java.io.InputStream


object ShaperParserFacade {

    fun parse(inputStream: InputStream) : Shaper {
        val lexer = ShaperLexer(CharStreams.fromStream(inputStream))
        val parser = ShaperParser(CommonTokenStream(lexer) as TokenStream)
        val antlrParsingResult = parser.shaper()
        return antlrParsingResult.toAst()
    }

}