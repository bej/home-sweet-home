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
import { Account } from './account';
import { Link } from './link';


export interface ResourceExpense {
    account?: Account;
    amount?: number;
    complete?: boolean;
    links?: Array<Link>;
    title?: string;
}
