<#include "base.ftl"/>

<#macro content>
    <table class="table table-hover">
        <thead>
            <tr>
                <th class="col-lg-1	col-xl-1">Id</th>
                <th class="col-lg-3	col-xl-2">Name</th>
                <th class="col-lg-3	col-xl-2">Price</th>
                <th class="col-lg-1	col-xl-2"></th>
            </tr>
        </thead>
        <tbody>
            <#list products as product>
            <tr>
                <td class="col-lg-1	col-xl-1">${product.id}</td>
                <td class="col-lg-1	col-xl-2">${product.name}</td>
                <td class="col-lg-1	col-xl-2">${product.price}</td>
                <td class="col-lg-1	col-xl-2">
                    <form action="/product/edit" method="GET" style="display:inline!important">
                        <button name="id" value=${product.id} class="btn btn-outline-primary" style="display:inline!important">
                        <span class="btn-label">
                            <i class="bi bi-pencil-fill"></i>
                        </span>
                        </button>
                    </form>
                    <form action="/" method="POST" style="display:inline!important">
                        <button name="id" value=${product.id} type="submit" class="btn btn-outline-danger">
                        <span class="btn-label">
                            <i class="bi bi-trash"></i>
                        </span>
                        </button>
                    </form>
                    <form action="/cart/add" method="POST" style="display:inline!important">
                         <button class="btn btn-labeled btn-outline-success" value=${product.id} name="id">
                            <span class="btn-label">
                                <i class="bi bi-cart-plus-fill"></i>
                            </span>
                        </button>
                    </form>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</#macro>

<@base/>