
<%--@elvariable id="shortText" type="java.lang.String"--%>
<%--@elvariable id="longText" type="java.lang.String"--%>

<!-- directoryName:fileName -->
<template:main htmlTitle="Abbreviating Text">
    <b>Short text:</b> ${jwa:abbreviateString(shortText, 32)}<br/>
    <b>Long text:</b> ${jwa:abbreviateString(longText, 32)}<br/>
</template:main>