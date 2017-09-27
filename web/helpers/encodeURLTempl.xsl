<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- This template is used for encoding URL:s -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template name="QuoteURL">
  <xsl:param name="text"/>
  <xsl:call-template name="ReplaceThisWithThat">
  <xsl:with-param name="text">
  <xsl:call-template name="ReplaceThisWithThat">
  <xsl:with-param name="text">
   <xsl:call-template name="ReplaceThisWithThat">
   <xsl:with-param name="text">
    <xsl:call-template name="ReplaceThisWithThat">
    <xsl:with-param name="text">
     <xsl:call-template name="ReplaceThisWithThat">
     <xsl:with-param name="text">
      <xsl:call-template name="ReplaceThisWithThat">
       <xsl:with-param name="text">
	<xsl:value-of select="$text"/>
       </xsl:with-param>
       <xsl:with-param name="this" select="'ä'"/>
       <xsl:with-param name="that" select="'%e4'"/>
      </xsl:call-template>
      </xsl:with-param>
      <xsl:with-param name="this" select="' '"/>
      <xsl:with-param name="that" select="'%20'"/>
     </xsl:call-template>  
     </xsl:with-param>
     <xsl:with-param name="this" select="'ö'"/>
     <xsl:with-param name="that" select="'%f6'"/>
    </xsl:call-template>
    </xsl:with-param>
    <xsl:with-param name="this" select="'Ä'"/>
    <xsl:with-param name="that" select="'%c4'"/>
   </xsl:call-template>
   </xsl:with-param>
   <xsl:with-param name="this" select="'Å'"/>
   <xsl:with-param name="that" select="'%c5'"/>
  </xsl:call-template>
  </xsl:with-param>
  <xsl:with-param name="this" select="'Ö'"/>
  <xsl:with-param name="that" select="'%d6'"/>
 </xsl:call-template>
</xsl:template>

<xsl:template name="ReplaceThisWithThat">
  <xsl:param name="text"/>
  <xsl:param name="this"/>
  <xsl:param name="that"/>
  <xsl:choose>
    <xsl:when test="contains($text,$this)">
      <xsl:value-of select="substring-before($text,$this)"/>
      <xsl:value-of select="$that"/>
      <xsl:call-template name="ReplaceThisWithThat">
        <xsl:with-param name="text" select="substring-after($text,$this)"/>
        <xsl:with-param name="this" select="$this"/>
        <xsl:with-param name="that" select="$that"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$text"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>

