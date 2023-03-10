import styled from "styled-components";
// import SignUpGoogle from "./SignUpGoogle";

const OpenIdContainer = styled.div`
  max-width: calc(calc(97rem / 12) * 3);
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
  margin-left: 0;
  margin-right: 0;
`;

const SignInButton = styled.button`
  display: flex;
  flex-direction: row;
  margin-right: 0;
  margin-left: 0;
  background-color: rgb(255, 255, 255);
  margin: 4px;
  flex: 1 auto;
  cursor: pointer;
  text-align: center;
  height: auto;
  width: auto;
  border-radius: 5px;
  border-color: hsl(210, 8% 85%);

  svg {
    width: 28px;
    height: 28px;
    margin: auto;
    margin-left: 10px;
  }

  &:hover {
    background-color: hsl(210, 2%, 82%);
  }
`;

const GithubButton = (
    <svg viewBox="0 0 18 18">
        <path fillRule="evenodd" d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38
      0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01
      1.08.58 1.23.82.72 1.21 1.87.87
      2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12
      0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08
      2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0
      .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/>
    </svg>
);

const GoogleButton = (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 533.5 544.3">
        <path
            d="M533.5 278.4c0-18.5-1.5-37.1-4.7-55.3H272.1v104.8h147c-6.1 33.8-25.7 63.7-54.4 82.7v68h87.7c51.5-47.4 81.1-117.4 81.1-200.2z"
            fill="#4285f4"/>
        <path
            d="M272.1 544.3c73.4 0 135.3-24.1 180.4-65.7l-87.7-68c-24.4 16.6-55.9 26-92.6 26-71 0-131.2-47.9-152.8-112.3H28.9v70.1c46.2 91.9 140.3 149.9 243.2 149.9z"
            fill="#34a853"/>
        <path
            d="M119.3 324.3c-11.4-33.8-11.4-70.4 0-104.2V150H28.9c-38.6 76.9-38.6 167.5 0 244.4l90.4-70.1z"
            fill="#fbbc04"/>
        <path
            d="M272.1 107.7c38.8-.6 76.3 14 104.4 40.8l77.7-77.7C405 24.6 339.7-.8 272.1 0 169.2 0 75.1 58 28.9 150l90.4 70.1c21.5-64.5 81.8-112.4 152.8-112.4z"
            fill="#ea4335"/>
    </svg>
);

const FaceBookButton = (
    <svg viewBox="0 0 17 17">
        <path
            d="M3 1a2 2 0 0 0-2 2v12c0 1.1.9 2 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H3Zm6.55 16v-6.2H7.46V8.4h2.09V6.61c0-2.07 1.26-3.2 3.1-3.2.88 0 1.64.07 1.87.1v2.16h-1.29c-1 0-1.19.48-1.19 1.18V8.4h2.39l-.31 2.42h-2.08V17h-2.5Z"
            fill="#4167B2"></path>
    </svg>
);

const GithubSignInButton = styled(SignInButton)`
  svg {
    margin-top: 5px;
    width: 32px;
    height: 32px;
  }
`;

const FaceBookSignInButton = styled(SignInButton)`
  svg {
    width: 32px;
    height: 32px;
  }
`;

const SignInMessage = styled.div`
  width: 100%;
  font-size: 15px;
  margin: 10px;
  text-align: center;
  color: black;
`;

const SignUpButtons = () => {
    return (
        <OpenIdContainer>
            {/*<SignUpGoogle/>*/}
            <SignInButton>
                {GoogleButton}
                <SignInMessage>
                    Sign up with Google
                </SignInMessage>
            </SignInButton>
            <GithubSignInButton>
                {GithubButton}
                <SignInMessage>
                    Sign up with GitHub
                </SignInMessage>
            </GithubSignInButton>
            <FaceBookSignInButton>
                {FaceBookButton}
                <SignInMessage>
                    Sign up with FaceBook
                </SignInMessage>
            </FaceBookSignInButton>
        </OpenIdContainer>
    )
};

export default SignUpButtons;