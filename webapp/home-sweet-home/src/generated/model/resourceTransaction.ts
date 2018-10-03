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
import { Expense } from './expense';
import { Income } from './income';
import { Link } from './link';


export interface ResourceTransaction {
    from?: Income;
    links?: Array<Link>;
    to?: Expense;
    value?: number;
}