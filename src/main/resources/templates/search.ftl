<#import "common.ftl" as com>

<@com.page>
    <h2>NEW FLOWERS</h2>
        <form action="/save" method="post">
            <table>
                <#list findFlowers as flower>
                    <tr>
                        <td class="flowers-names" style="width: 100%">${flower.getName()}</td>
                        <td><input class="form-checkbox-button" type="checkbox" name="${flower.getId()}"></td>
                    </tr>
                <#else>
                    <div>Nothing to add :(</div>
                </#list>
            </table>
            <div class="form-buttons-container">
                <#if findFlowers?has_content>
                    <input class="form-submit-reset-button" type="submit" value="Save">
                </#if>
                <input class="form-submit-reset-button" type="button" value="Update" onclick="window.location.reload()">
            </div>
        </form>
    <a href="/flowers">BACK TO MAIN PAGE</a>
</@com.page>