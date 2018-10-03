/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { View } from './view';


export interface ModelAndView {
    empty?: boolean;
    model?: any;
    modelMap?: { [key: string]: any; };
    reference?: boolean;
    status?: ModelAndView.StatusEnum;
    view?: View;
    viewName?: string;
}
export namespace ModelAndView {
    export type StatusEnum = '100' | '101' | '102' | '103' | '200' | '201' | '202' | '203' | '204' | '205' | '206' | '207' | '208' | '226' | '300' | '301' | '302' | '303' | '304' | '305' | '307' | '308' | '400' | '401' | '402' | '403' | '404' | '405' | '406' | '407' | '408' | '409' | '410' | '411' | '412' | '413' | '414' | '415' | '416' | '417' | '418' | '419' | '420' | '421' | '422' | '423' | '424' | '426' | '428' | '429' | '431' | '451' | '500' | '501' | '502' | '503' | '504' | '505' | '506' | '507' | '508' | '509' | '510' | '511';
    export const StatusEnum = {
        _100: '100' as StatusEnum,
        _101: '101' as StatusEnum,
        _102: '102' as StatusEnum,
        _103: '103' as StatusEnum,
        _200: '200' as StatusEnum,
        _201: '201' as StatusEnum,
        _202: '202' as StatusEnum,
        _203: '203' as StatusEnum,
        _204: '204' as StatusEnum,
        _205: '205' as StatusEnum,
        _206: '206' as StatusEnum,
        _207: '207' as StatusEnum,
        _208: '208' as StatusEnum,
        _226: '226' as StatusEnum,
        _300: '300' as StatusEnum,
        _301: '301' as StatusEnum,
        _302: '302' as StatusEnum,
        _303: '303' as StatusEnum,
        _304: '304' as StatusEnum,
        _305: '305' as StatusEnum,
        _307: '307' as StatusEnum,
        _308: '308' as StatusEnum,
        _400: '400' as StatusEnum,
        _401: '401' as StatusEnum,
        _402: '402' as StatusEnum,
        _403: '403' as StatusEnum,
        _404: '404' as StatusEnum,
        _405: '405' as StatusEnum,
        _406: '406' as StatusEnum,
        _407: '407' as StatusEnum,
        _408: '408' as StatusEnum,
        _409: '409' as StatusEnum,
        _410: '410' as StatusEnum,
        _411: '411' as StatusEnum,
        _412: '412' as StatusEnum,
        _413: '413' as StatusEnum,
        _414: '414' as StatusEnum,
        _415: '415' as StatusEnum,
        _416: '416' as StatusEnum,
        _417: '417' as StatusEnum,
        _418: '418' as StatusEnum,
        _419: '419' as StatusEnum,
        _420: '420' as StatusEnum,
        _421: '421' as StatusEnum,
        _422: '422' as StatusEnum,
        _423: '423' as StatusEnum,
        _424: '424' as StatusEnum,
        _426: '426' as StatusEnum,
        _428: '428' as StatusEnum,
        _429: '429' as StatusEnum,
        _431: '431' as StatusEnum,
        _451: '451' as StatusEnum,
        _500: '500' as StatusEnum,
        _501: '501' as StatusEnum,
        _502: '502' as StatusEnum,
        _503: '503' as StatusEnum,
        _504: '504' as StatusEnum,
        _505: '505' as StatusEnum,
        _506: '506' as StatusEnum,
        _507: '507' as StatusEnum,
        _508: '508' as StatusEnum,
        _509: '509' as StatusEnum,
        _510: '510' as StatusEnum,
        _511: '511' as StatusEnum
    }
}
