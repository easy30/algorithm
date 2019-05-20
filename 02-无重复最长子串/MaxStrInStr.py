def getStr(str):
    if not str:
        return None
    resList = []
    maxStrList = []
    for charStr in str:
        if charStr in resList:
            if len(maxStrList) < len(resList):
                maxStrList = resList.copy()
            while True:
                leftItem = resList.pop(0)
                if leftItem == charStr:
                    resList.append(charStr)
                    break
        else:
            resList.append(charStr)

    if len(maxStrList) < len(resList):
        maxStrList = resList.copy()

    return maxStrList

def getStr2(str):
    if not str:
        return None
    charDict = {}
    maxLen = 0
    tmpMaxLen = 0
    nowIndex = 0
    conIndex = 0
    leftIndex = 0
    for charStr in str:
        conIndex = charDict.get(charStr)
        if conIndex != None:
            #print('max:',maxLen,'t:',tmpMaxLen, 'c:',conIndex, 'l:',leftIndex,'n:',nowIndex)
            if conIndex >= leftIndex:
                if maxLen < tmpMaxLen:
                    maxLen = tmpMaxLen
                tmpMaxLen = tmpMaxLen - (conIndex + 1 - leftIndex)
                leftIndex = conIndex + 1
            #print('max:', maxLen, 't:', tmpMaxLen, 'c:', conIndex, 'l:', leftIndex, 'n:af')
            #print('---------------------------------')
        tmpMaxLen += 1
        charDict[charStr] = nowIndex
        nowIndex += 1

    if maxLen < tmpMaxLen:
        maxLen = tmpMaxLen

    return maxLen



if __name__ == '__main__':
    print(getStr("aaaaaaabvaaaaa"))
    print(getStr2("aaaaaaabvaaaaa"))