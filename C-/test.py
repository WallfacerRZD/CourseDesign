if __name__ == "__main__":
    lines1 = []
    lines2 = []
    with open("c2.txt", "r") as f1:
        lines1 = f1.readlines()
    with open("c1.txt", "r") as f2:
        lines2 = f2.readlines()
    
    for i in range(min(len(lines1), len(lines2))):
        if (lines1[i] != lines2[i]):
            print("----------")
            print(i, lines1[i], lines2[i])
            print("----------")
    print("the same", lines1 == lines2)