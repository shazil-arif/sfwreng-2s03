import os
import sys
import shutil
from shutil import copyfile
import subprocess


students = {}
def readFile(fileName, data):
    file = open(fileName, "r")
    Lines = file.readlines()
    count = 0
    for line in Lines:
        #OrgDefinedId,Username,Last Name,First Name,Email
        info = line.split(',')
        '''
        info[0] = student number
        info[1] = macid
        info[2] = last name
        info[3] = first name
        info[4] = email
        '''

        if(count > 0):
            studentNumber = info[0]
            macid = info[1]
            lastName = info[2]
            firstName = info[3]
            email = info[4]
            key = lastName + '-' + firstName
            if(key not in data):
                data[key] = {}
                data[key]['student_number'] = studentNumber
                data[key]['mac_id'] = macid
                data[key]['last_name'] = lastName
                data[key]['first_name'] = firstName
                data[key]['email'] = email
        count += 1

    file.close()

def writeLog(log, key):
    f = open("./log.txt", "a")
    f.write("{OrgDefinedId},{Username},{LastName},{FirstName},{Email}".format(
                OrgDefinedId = students[key]['student_number'], 
                Username = students[key]['mac_id'],
                LastName = students[key]['last_name'],
                FirstName= students[key]['first_name'] ,
                Email = students[key]['email']
            ))
    f.write("\n")
    f.writelines(log)
    f.write("\n")
    f.close()
    
 
def writeGrade(points, key):
    f = open("./grades.txt", "a")
    f.write("{OrgDefinedId},{Username},{LastName},{FirstName},{Email},{grade}".format(
                OrgDefinedId = students[key]['student_number'], 
                Username = students[key]['mac_id'],
                LastName = students[key]['last_name'],
                FirstName= students[key]['first_name'] ,
                Email = students[key]['email'],
                grade = points
            ))
    f.write("\n")
    f.close()
    

# check if two 2d arrays are equal
def assert2DArray(arr1, arr2):
    for i in range(len(arr1)):
        try:
            for j in range(len(arr1[i])):
                if(arr1[i][j] != arr2[i][j]): return False
        except:
            return False
    return True

def checkGrid(studentAnswers):
    log = []
    answers = [
        [
            [
                [0,0,0,0],
                [0,0,0,0],
                [0,0,0,0],
                [0,8,0,0]
            ]
       
        ],
        [
            [
                [0,0,0,0],
                [0,0,0,0],
                [0,0,0,0],
                [0,8,0,9]
            ]
        ],
        [
            [
                [0,0,0,0],
                [0,0,0,0],
                [0,0,0,0],
                [0,8,6,9]
            ]
            
        ],
        [
            [
                [0,0,0,0],
                [0,0,0,0],
                [0,1,0,0],
                [0,8,6,9]
            ]
        ],
        [
            [ [5] ],

        ],
        [
            [ [5] ],
        ],
        [
            [
                [0,3,0,0],
                [0,2,0,0],
                [0,1,1,0],
                [0,8,6,9]
            ],
            [

                #or

                [0,3,0,0],
                [0,2,0,0],
                [0,1,0,0],
                [1,8,6,9]
            ]
        ],
        [
            
            [
                [0,3,0,0],
                [0,2,2,0],
                [0,1,1,0],
                [0,8,6,9]
            ],

                # or
            [
                [0,3,0,0],
                [0,2,2,0],
                [2,1,1,0],
                [1,8,6,9]
            ]
        ],
        [
            [
                [0,3,3,0],
                [0,2,2,0],
                [0,1,1,0],
                [0,8,6,9]
            ],

            # or
            [
                [0,3,3,0],
                [3,2,2,0],
                [2,1,1,0],
                [1,8,6,9]
            ]
        ],
    ]

    points = 0
    for i in range(len(answers)):
        passedAny = False
        try:
            for any in answers[i]: # try different possible values
                if(assert2DArray(any, studentAnswers[i])): 
                    log.append('Expecting' + str(any) + ' and ' + 'got' + str(studentAnswers[i]) + 'Result: Passed \n')
                    points += 1 
                    passedAny = True
                    break
            if(passedAny == False):
                log.append('Expecting' + str(answers[i]) + ' and ' + 'got' + str(studentAnswers[i]) + 'Result: Failed \n')


        except Exception as e:
            log.append('Testing' + str(answers[i]) + 'Exception: ' + str(e) + ' \n')
            pass    

    
    return [points, log]

            


def createStudentGridFromStdOut(output):
    allGrids = []
    grid = []

    for stringArray in output:
        i = 1
        row = []
        while(i < len(stringArray) - 1):
            try:

                if(stringArray[i] != ','): row.append(int(stringArray[i]))
            except:
                return []
            i += 1
        grid.append(row)

    k = 0
    j = 0
    row = []
    while(j < len(grid)):
        if(len(grid[j]) == 1):
            allGrids.append([grid[j]])

        elif((k + 1) % 4 == 0 and i != 0): 
            allGrids.append(row)
            row.append(grid[j])
            k += 1
            row = []

        else: 
            row.append(grid[j])
            k += 1

        j += 1
    
    return allGrids  


def main():
    if(len(sys.argv) == 1):
        print('Path to directory required')
        exit(0)

    readFile("students.csv.txt", students)


    files = [f for f in os.listdir(sys.argv[1])]
    seen = {}
    count = 0

    for f in files:
        info = f.split()
        '''
        info[-1] = filename
        '''

        if(info[-1] == 'Grid.java'):
            first = info[2]
            last = info[3]
            key = last + '-' + first


            '''
            info[5] 6 7 8 9 = Time
            '''
            timestamp = info[5] + ' ' + info[6]  + ' ' +  info[7]  + ' ' +  info[8]  + ' ' +  info[9] 
           

            if(key in seen):
                print('Duplicate found for {student}. Already Graded {time} submission. Verify manually '.format(student = key,time=seen[key]))

            else:
                path = './{folderName}/{last}-{first}'.format(folderName = sys.argv[1],last=last, first=first)
                studentFile = '/{fileName}'.format(fileName=f)

                os.mkdir(path)

                copyfile('./tests.java',  path + '/tests.java')

                #copyfile('./' + sys.argv[1] + studentFile, path + '/Assignment1.java')

                copyfile('./' + sys.argv[1] + studentFile, path + '/Grid.java')

                bashCommand = "javac tests.java"
                process = subprocess.Popen(bashCommand, stdout=subprocess.PIPE, cwd=path)
                output, error = process.communicate()

                
                bashCommand = "java tests"
                process = subprocess.Popen(bashCommand, stdin=subprocess.PIPE, stdout=subprocess.PIPE, cwd=path, stderr=subprocess.STDOUT)
                output, error = process.communicate()

                # print(str(output, 'utf-8').strip().split())
                # print(len(str(output, 'utf-8').strip().split()))
                if(error == None):
                    grid = createStudentGridFromStdOut(str(output, 'utf-8').strip().split())
                    
                else:
                    print('Error found for {first} {last}'.format(first=students[key]['first_name'], last = students[key]['last_name'] ))
                shutil.rmtree(path)
                seen[key] = timestamp # last submission graded
                count += 1

                # if(200004748 == students[key]['student_number']): 
                #     for row in grid: print(row)
                returnValue = checkGrid(grid)
                points = returnValue[0]
                log = returnValue[1]
                
                writeGrade(points, key)
                writeLog(log, key)
                # if(count > 15): break



    marked = {}
    readFile("grades.txt", marked)
    for key in students.keys():
        if(key not in marked): print('No Submission or grade for {student}'.format(student=key))

main()
