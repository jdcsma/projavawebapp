<%--@elvariable id="date" type="java.util.Date"--%>
<%--@elvariable id="calender" type="java.util.Calendar"--%>
<%--@elvariable id="instant" type="java.time.Instant"--%>
<template:main htmlTitle="Display Dates Property">
    <b>Date:</b>
    <jwa:formatDate value="${date}" type="both" dateStyle="full"
                    timeStyle="full" timeFirst="true" separateDateTimeWith=" on "/>
    <br/>
    <b>Calender:</b>
    <jwa:formatDate value="${calender}" type="both" dateStyle="full"
                    timeStyle="full"/>
    <br/>
    <b>Instant, time first with separator:</b>
    <jwa:formatDate value="${instant}" type="both" dateStyle="full"
                    timeStyle="full" timeFirst="true" separateDateTimeWith=" on "/>
</template:main>
