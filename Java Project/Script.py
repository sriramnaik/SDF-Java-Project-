from compile import compile_run

def load_test_cases(filename="default_test_case.txt"):
    try:
        with open(filename) as f:
            test_cases = []
            for line in f:
                line = line.strip("\n")
                test_cases.append(line.split(" "))
            return test_cases
    except FileNotFoundError: 
        return []

def save_test_cases(test_cases,filename="default_test_case.txt"):
    with open(filename,"a") as f:
        f.write(" ".join(test_cases) + "\n")
    
def main():
    default_test_cases = load_test_cases()

    while True:
        user_input = input("Enter the input (or leave it empty to run default test cases):\n").split(" ")
        if user_input == ['n']:
            break

        for i in range(user_input.count("")):
            user_input.remove("")
        if not user_input:
            print("The input provided is empty.\nWould you like to run the default test case or enter the input again? (y means run the default cases):")
            choice = input()
            if choice == 'y': 
                for test_case in default_test_cases:
                    compile_run(test_case)
            else: 
                continue

        elif len(user_input) != 4:
            print("Invalid number of arguments. Please provide exactly 4 values.")
            continue 
            
        else : 
            compile_run(user_input)
            print("Do you want to add this to the default test cases? (y/n)")
            choice = input()
            if choice == 'y' : 
                save_test_cases(user_input)
                print("Test case saved to default_cases.txt.")
                
            print("Do you want to test another test case? (y/n):")
            c = input()
            if c.lower() != 'y':
                break  

main()