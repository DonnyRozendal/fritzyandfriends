pragma solidity >=0.4.22 <=0.5.15;

contract Fritzy {

    struct Transaction {
        address localNetty;
        bool isSelling;
        uint power;
    }


    mapping(address => Transaction[]) private _transaction;
    mapping(address => bool[]) private _isSellingArray;
    mapping(address => uint[]) private _powerArray;

    function makeTransaction(bool _selling, uint _power) public {
        // Transaction memory trans = Transaction(msg.sender, _selling, _power);
        _isSellingArray[msg.sender].push(_selling);
        _powerArray[msg.sender].push(_power);
    }

    function getPowerArray() public view returns (uint[] memory){
        return _powerArray[msg.sender];
    }

    function getIsSellingArray() public view returns (bool[] memory) {
        return _isSellingArray[msg.sender];
    }


}
