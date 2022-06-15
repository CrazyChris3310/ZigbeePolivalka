<#import "common.ftl" as com>

<@com.page>
    <div class="container">
        <h2>POLIVALKA</h2>
        <#list flowerList as flower>
            <div class="row center-md center-sm">
                <div class="col-md-5 col-sm-7 col-xs-9 end-xs">${flower.getName()}</div>
                <div class="col-md-1 col-sm-3 col-xs-2 end-xs">${flower.getCurrentMoistureLevel()}%</div>
                <div class="col-md-2 col-sm-4 col-xs-6 end-xs"><a href="/${flower.getId()}">EDIT</a></div>
                <div class="col-md-2 col-sm-4 col-xs-6"><a href="/delete/${flower.getId()}">Remove</a></div>
            </div>
        </#list>
        <div class="row center-md" >
            <a href="/search">ADD NEW FLOWERS</a>
        </div>
    </div>
</@com.page>